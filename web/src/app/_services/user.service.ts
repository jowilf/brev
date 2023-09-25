import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserRegister } from '../_models/user-register';
import { environment } from 'src/environments/environment.development';
import { Observable, map } from 'rxjs';
import { User } from '../_models/user';
import { LoginRequest } from '../_models/login-request';
import { TokenResponse } from '../_models/token-response';
import { TokenService } from './token.service';
import { PageResponse } from '../_models/page-response';
import { ShortenUrlResponse } from '../_models/shorten-url-response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient, private tokenService: TokenService) {}

  public register(userRegister: UserRegister): Observable<User> {
    return this.http.post<User>(
      `${environment.USER_BASE_URL}/api/v1/auth/register`,
      userRegister
    );
  }

  public login(loginRequest: LoginRequest) {
    return this.http
      .post<TokenResponse>(
        `${environment.USER_BASE_URL}/api/v1/auth/login`,
        loginRequest
      )
      .pipe(
        map((token) => {
          this.tokenService.saveToken(token.accessToken);
          return token;
        })
      );
  }

}

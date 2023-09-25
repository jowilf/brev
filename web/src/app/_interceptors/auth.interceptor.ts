import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { TokenService } from '../_services/token.service';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const authToken = this.tokenService.getToken();
    let authReq = request;
    if (authToken) {
      authReq = request.clone({
        setHeaders: { Authorization: `Bearer ${authToken}` },
      });
    }
    return next.handle(authReq).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status == 401 || err.status == 403) {
          const currentRoute = this.router.url;
          this.router.navigate(['/login'], {
            queryParams: { next: currentRoute },
          });
        }
        return throwError(() => err);
      })
    );
  }
}

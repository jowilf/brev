import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { ShortenUrlRequest } from '../_models/shorten-url-request';
import { ShortenUrlResponse } from '../_models/shorten-url-response';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { PageResponse } from '../_models/page-response';

@Injectable({
  providedIn: 'root',
})
export class UrlService {
  constructor(private http: HttpClient) {}

  public shortenUrl(
    request: ShortenUrlRequest
  ): Observable<ShortenUrlResponse> {
    return this.http.post<ShortenUrlResponse>(
      `${environment.URL_BASE_URL}/api/v1/urls`,
      request
    );
  }

  public getMyLinks(page: number, size: number) {
    return this.http.get<PageResponse<ShortenUrlResponse>>(
      `${environment.URL_BASE_URL}/api/v1/urls`,
      { params: { page: page, size: size } }
    );
  }
}

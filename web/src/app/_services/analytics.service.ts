import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AnalyticsResponse } from '../_models/analytics-response';
import { environment } from 'src/environments/environment.development';
import { Observable } from 'rxjs';
import { Dayjs } from 'dayjs';

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {
  constructor(private http: HttpClient) {}

  public getAnalytics(
    shortUrl: string,
    start: Dayjs,
    end: Dayjs,
    aggregateType?: 'minute' | 'hour' | 'day'
  ): Observable<AnalyticsResponse[]> {
    return this.http.get<AnalyticsResponse[]>(
      `${environment.ANALYTICS_BASE_URL}/api/v1/analytics`,
      {
        params: {
          startDate: start.toISOString(),
          endDate: end.toISOString(),
          aggregateType: aggregateType,
          limit: 1000,
          shortUrl: shortUrl,
        },
      }
    );
  }
}

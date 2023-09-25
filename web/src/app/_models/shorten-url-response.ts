import { UrlTree } from '@angular/router';

export class ShortenUrlResponse {
  constructor(
    public originalUrl?: string,
    public shortUrl?: string,
    public path?: string,
    public createdAt?: Date
  ) {}
}

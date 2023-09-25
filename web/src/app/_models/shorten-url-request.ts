export class ShortenUrlRequest {
  constructor(
    public originalUrl?: string,
    public customShortUrl?: string,
    public expiresAt?: Date
  ) {}
}

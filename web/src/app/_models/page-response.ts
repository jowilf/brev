import { ShortenUrlResponse } from './shorten-url-response';

export class PageResponse<T> {
  constructor(
    public data: T[],
    public currentPage: number,
    public totalItems: number,
    public totalPages: number
  ) {}
}

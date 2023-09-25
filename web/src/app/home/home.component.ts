import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ShortenUrlRequest } from '../_models/shorten-url-request';
import { UrlService } from '../_services/url.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PageResponse } from '../_models/page-response';
import { ShortenUrlResponse } from '../_models/shorten-url-response';
import { AnalyticsModalComponent } from '../analytics-modal/analytics-modal.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements AfterViewInit {
  invalidCredentials?: string;
  errors: any = {};
  shortenUrlRequest = new ShortenUrlRequest();
  myLinks?: PageResponse<ShortenUrlResponse>;
  loading = false;
  constructor(
    private urlService: UrlService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  @ViewChild(AnalyticsModalComponent) modal!: AnalyticsModalComponent;
  ngAfterViewInit(): void {
    this.loadLink();
  }

  onSubmit() {
    this.loading = true;
    this.errors = {};
    if (this.shortenUrlRequest.customShortUrl == '')
      this.shortenUrlRequest.customShortUrl = undefined;

    this.urlService.shortenUrl(this.shortenUrlRequest).subscribe({
      next: (response) => {
        this.shortenUrlRequest = new ShortenUrlRequest();
        this.loading = false;
        this.loadLink();
        console.log(response);
      },
      error: (err) => {
        console.log(err.error);
        if (err.status == 400) {
          this.errors = err.error.errors;
        } else if (err.status == 409) {
          this.errors = { customShortUrl: err.error.message };
        }
        this.loading = false;
      },
    });
  }

  loadLink() {
    let page = this.myLinks?.currentPage || 0;
    let size = 3;
    this.urlService.getMyLinks(page, size).subscribe({
      next: (res) => {
        this.myLinks = res;
        console.log(res);
      },
    });
  }

  previousPage() {
    if (this.myLinks && this.myLinks.currentPage > 0) {
      this.myLinks.currentPage -= 1;
      this.loadLink();
    }
  }
  nextPage() {
    if (
      this.myLinks &&
      this.myLinks.currentPage + 1 < this.myLinks.totalPages
    ) {
      this.myLinks.currentPage += 1;
      this.loadLink();
    }
  }

  showAnalytics(shortenUrlResponse: ShortenUrlResponse) {
    this.modal.show(shortenUrlResponse.path);
  }
}

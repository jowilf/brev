import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../_services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginRequest } from '../_models/login-request';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  errors: any = {};

  invalidCredentials?: string;

  loginRequest = new LoginRequest();

  loading = false;

  onSubmit() {
    this.loading = true;
    this.errors = {};
    this.userService.login(this.loginRequest).subscribe({
      next: (token) => {
        const returnUrl = this.route.snapshot.queryParams['next'] || '/';
        this.router.navigateByUrl(returnUrl);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status == 400) {
          this.errors = err.error.errors;
        } else if (err.status == 404) {
          this.invalidCredentials = err.error.message;
        } else {
          this.invalidCredentials = 'Something went wrong!';
        }
        this.loading = false;
      },
    });
  }
}

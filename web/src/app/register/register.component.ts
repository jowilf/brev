import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRegister } from '../_models/user-register';
import { FormsModule } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../_models/user';
import { LoginRequest } from '../_models/login-request';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  userRegister = new UserRegister();

  errors: any = {};

  loading = false;

  onSubmit() {
    this.loading = true;
    this.userService.register(this.userRegister).subscribe({
      next: (user: User) => {
        console.log(user);
        this.errors = {};
        this.login(new LoginRequest(user.username, this.userRegister.password));
      },
      error: (err: HttpErrorResponse) => {
        if (err.status == 400) {
          this.errors = err.error.errors;
        } else if (err.status == 409) {
          this.errors = { username: err.error.message };
        }
        this.loading = false;
      },
    });
  }

  login(loginRequest: LoginRequest) {
    this.userService.login(loginRequest).subscribe({
      next: (token) => {
        const returnUrl = this.route.snapshot.queryParams['next'] || '/';
        this.router.navigateByUrl(returnUrl);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}

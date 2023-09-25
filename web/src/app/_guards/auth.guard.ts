import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../_services/token.service';

export const authGuard: CanActivateFn = (route, state) => {
  if (inject(TokenService).getToken()) return true;
  inject(Router).navigate(['/login']);
  return false;
};

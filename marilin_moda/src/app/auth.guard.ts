import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './services/tools/auth.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  if (authService.isLoggedIn1()) {
    return true; // Permite el acceso si el usuario está autenticado
  } else {
    router.navigate(['/login']); // Redirige al login si no está autenticado
    return false;
  }
};

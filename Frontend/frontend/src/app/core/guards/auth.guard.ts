import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private storageService: StorageService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    if (!this.authService.estaAutenticado()) {
      this.storageService.limpiar();
      this.router.navigate(['/login']);
      return false;
    }

    const requiereSuperadmin = route.data['superadmin'];
    const soloClientes = route.data['soloClientes'];

    if (requiereSuperadmin && !this.storageService.esSuperadmin()) {
      this.router.navigate(['/dashboard']);
      return false;
    }

    if (soloClientes && this.storageService.esSuperadmin()) {
      this.router.navigate(['/panelcontrol']);
      return false;
    }

    return true;
  }
}
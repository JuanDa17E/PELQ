import { Routes } from '@angular/router';
import { LoginComponent } from './core/components/login/login.component';
import { MainLayoutComponent } from './core/shared/layouts/main/main-layout.component';
import { DashboardComponent } from './core/components/dashboard/dashboard.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
    ]
  }
];
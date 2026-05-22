import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './core/components/login/login.component';
import { DashboardComponent } from './core/components/dashboard/dashboard.component';
import { MainLayoutComponent } from './core/shared/layouts/main/main-layout.component';
import { AgendaComponent } from './core/components/agenda/agenda.component';
import { PanelcontrolComponent } from './core/components/panelcontrol/panelcontrol.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'agenda', component: AgendaComponent },
      { path: 'panelcontrol', component: PanelcontrolComponent },
    ]
  },
  { path: '**', redirectTo: 'login' }
];
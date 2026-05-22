import { Component, OnInit, OnDestroy } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { TopbarComponent } from '../topbar/topbar.component';
import { inactividadService } from '../../../services/inactividad.service';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterModule, SidebarComponent, TopbarComponent],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss'
})
export class MainLayoutComponent implements OnInit, OnDestroy {

  constructor(private inactividadService: inactividadService) {}

  ngOnInit(): void {
    this.inactividadService.iniciar();
  }

  ngOnDestroy(): void {
    this.inactividadService.detener();
  }
}
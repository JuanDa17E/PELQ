import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cliente, panelcontrolService } from '../../services/panelcontrol.service';

interface NuevoCliente {
  nombreLocal: string;
  nombreContacto: string;
  telefono: string;
  emailLocal: string;
  dbUrl: string;
  fechaVencimiento: string;
  emailAdmin: string;
  passwordAdmin: string;
  dbUsername: string;
  dbPassword: string;
}

@Component({
  selector: 'app-panelcontrol',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './panelcontrol.component.html',
  styleUrl: './panelcontrol.component.scss'
})
export class PanelcontrolComponent implements OnInit {

  mostrarFormulario: boolean = false;
  clientes: Cliente[] = [];
  cargando: boolean = false;

 nuevoCliente: NuevoCliente = {
  nombreLocal: '',
  nombreContacto: '',
  telefono: '',
  emailLocal: '',
  dbUrl: '',
  dbUsername: '',
  dbPassword: '',
  fechaVencimiento: '',
  emailAdmin: '',
  passwordAdmin: ''
};

  constructor(private panelcontrolService: panelcontrolService,private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes(): void {
    this.cargando = true;
    this.panelcontrolService.listarClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  registrarCliente(): void {
    this.cargando = true;
    this.panelcontrolService.registrarCliente(this.nuevoCliente).subscribe({
      next: () => {
        this.mostrarFormulario = false;
        this.cargando = false;
        this.nuevoCliente = {
          nombreLocal: '',
          nombreContacto: '',
          telefono: '',
          emailLocal: '',
          dbUrl: '',
          dbUsername: '',
          dbPassword: '',
          fechaVencimiento: '',
          emailAdmin: '',
          passwordAdmin: ''
        };
        this.cargarClientes();
      },
      error: () => {
        this.cargando = false;
      }
    });
  }
}
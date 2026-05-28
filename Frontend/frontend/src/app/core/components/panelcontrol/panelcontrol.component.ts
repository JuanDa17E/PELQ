import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cliente, panelcontrolService } from '../../services/panelcontrol.service';
import { txt } from '../../constantes/txt.constants';

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

interface EditarCliente {
  nombreLocal: string;
  nombreContacto: string;
  telefono: string;
  emailLocal: string;
  fechaVencimiento: string;
  activo: boolean;
}

@Component({
  selector: 'app-panelcontrol',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './panelcontrol.component.html',
  styleUrl: './panelcontrol.component.scss'
})
export class PanelcontrolComponent implements OnInit {
    t = txt;
  clientes: Cliente[] = [];
  cargando: boolean = false;
  mostrarModalCrear: boolean = false;
  mostrarModalEditar: boolean = false;
  clienteSeleccionado: Cliente | null = null;

  nuevoCliente: NuevoCliente = this.clienteVacio();
  clienteEditar: EditarCliente = this.editarVacio();

  constructor(
    private panelcontrolService: panelcontrolService,
    private cdr: ChangeDetectorRef
  ) {}

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
        this.mostrarModalCrear = false;
        this.cargando = false;
        this.nuevoCliente = this.clienteVacio();
        this.cargarClientes();
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  abrirEditar(cliente: Cliente): void {
    this.clienteSeleccionado = cliente;
    this.clienteEditar = {
      nombreLocal: cliente.nombreLocal,
      nombreContacto: cliente.nombreContacto,
      telefono: cliente.telefono || '',
      emailLocal: cliente.email,
      fechaVencimiento: cliente.fechaVencimiento,
      activo: cliente.activo
    };
    this.mostrarModalEditar = true;
  }

  guardarEdicion(): void {
    if (!this.clienteSeleccionado) return;
    this.cargando = true;
    this.panelcontrolService.editarCliente(this.clienteSeleccionado.id, this.clienteEditar).subscribe({
      next: () => {
        this.mostrarModalEditar = false;
        this.cargando = false;
        this.cargarClientes();
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  toggleActivo(cliente: Cliente): void {
    this.panelcontrolService.toggleActivo(cliente.id).subscribe({
      next: () => this.cargarClientes(),
      error: () => {}
    });
  }

  eliminarCliente(cliente: Cliente): void {
    if (!confirm(`¿Eliminar ${cliente.nombreLocal}?`)) return;
    this.panelcontrolService.eliminarCliente(cliente.id).subscribe({
      next: () => this.cargarClientes(),
      error: () => {}
    });
  }

  cerrarModales(): void {
    this.mostrarModalCrear = false;
    this.mostrarModalEditar = false;
    this.clienteSeleccionado = null;
  }

  private clienteVacio(): NuevoCliente {
    return {
      nombreLocal: '', nombreContacto: '', telefono: '',
      emailLocal: '', dbUrl: '', dbUsername: '', dbPassword: '',
      fechaVencimiento: '', emailAdmin: '', passwordAdmin: ''
    };
  }

  private editarVacio(): EditarCliente {
    return {
      nombreLocal: '', nombreContacto: '', telefono: '',
      emailLocal: '', fechaVencimiento: '', activo: true
    };
  }
}
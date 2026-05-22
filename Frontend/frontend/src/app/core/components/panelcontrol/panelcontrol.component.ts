import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Cliente {
  id: string;
  nombreLocal: string;
  nombreContacto: string;
  email: string;
  fechaVencimiento: Date;
  activo: boolean;
}

@Component({
  selector: 'app-panelcontrol',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './panelcontrol.component.html',
  styleUrl: './panelcontrol.component.scss'
})
export class PanelcontrolComponent implements OnInit {

  mostrarFormulario: boolean = false;

  clientes: Cliente[] = [];

  ngOnInit(): void {}
}
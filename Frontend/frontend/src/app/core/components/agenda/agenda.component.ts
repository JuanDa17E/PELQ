import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

type Vista = 'semana' | 'mes' | 'año';

interface Cita {
  id: string;
  titulo: string;
  fecha: Date;
  horaInicio: string;
  horaFin: string;
  color: string;
}

@Component({
  selector: 'app-agenda',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agenda.component.html',
  styleUrl: './agenda.component.scss'
})
export class AgendaComponent implements OnInit {

  vistaActual: Vista = 'semana';
  fechaActual: Date = new Date();
  hoy: Date = new Date();

  horas: string[] = Array.from({ length: 14 }, (_, i) => {
    const hora = i + 7;
    return `${hora.toString().padStart(2, '0')}:00`;
  });

  meses: string[] = [
    'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
  ];

  diasSemana: string[] = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb'];
  diasSemanaCompleto: string[] = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];

  citas: Cita[] = [
    {
      id: '1',
      titulo: 'Carlos Méndez - Corte',
      fecha: new Date(),
      horaInicio: '09:00',
      horaFin: '09:30',
      color: '#C9A84C'
    },
    {
      id: '2',
      titulo: 'María López - Tinte',
      fecha: new Date(),
      horaInicio: '10:00',
      horaFin: '12:00',
      color: '#5a7aae'
    }
  ];

  ngOnInit(): void {}

  cambiarVista(vista: Vista): void {
    this.vistaActual = vista;
  }

  anterior(): void {
    const fecha = new Date(this.fechaActual);
    if (this.vistaActual === 'semana') {
      fecha.setDate(fecha.getDate() - 7);
    } else if (this.vistaActual === 'mes') {
      fecha.setMonth(fecha.getMonth() - 1);
    } else {
      fecha.setFullYear(fecha.getFullYear() - 1);
    }
    this.fechaActual = fecha;
  }

  siguiente(): void {
    const fecha = new Date(this.fechaActual);
    if (this.vistaActual === 'semana') {
      fecha.setDate(fecha.getDate() + 7);
    } else if (this.vistaActual === 'mes') {
      fecha.setMonth(fecha.getMonth() + 1);
    } else {
      fecha.setFullYear(fecha.getFullYear() + 1);
    }
    this.fechaActual = fecha;
  }

  irAHoy(): void {
    this.fechaActual = new Date();
  }

  getTituloNavegacion(): string {
    if (this.vistaActual === 'semana') {
      const dias = this.getDiasSemana();
      const inicio = dias[0];
      const fin = dias[6];
      if (inicio.getMonth() === fin.getMonth()) {
        return `${inicio.getDate()} - ${fin.getDate()} ${this.meses[inicio.getMonth()]} ${inicio.getFullYear()}`;
      }
      return `${inicio.getDate()} ${this.meses[inicio.getMonth()]} - ${fin.getDate()} ${this.meses[fin.getMonth()]} ${inicio.getFullYear()}`;
    } else if (this.vistaActual === 'mes') {
      return `${this.meses[this.fechaActual.getMonth()]} ${this.fechaActual.getFullYear()}`;
    }
    return `${this.fechaActual.getFullYear()}`;
  }

  getDiasSemana(): Date[] {
    const dias: Date[] = [];
    const fecha = new Date(this.fechaActual);
    const dia = fecha.getDay();
    fecha.setDate(fecha.getDate() - dia);
    for (let i = 0; i < 7; i++) {
      dias.push(new Date(fecha));
      fecha.setDate(fecha.getDate() + 1);
    }
    return dias;
  }

  getDiasMes(): (Date | null)[] {
    const dias: (Date | null)[] = [];
    const primerDia = new Date(this.fechaActual.getFullYear(), this.fechaActual.getMonth(), 1);
    const ultimoDia = new Date(this.fechaActual.getFullYear(), this.fechaActual.getMonth() + 1, 0);
    for (let i = 0; i < primerDia.getDay(); i++) {
      dias.push(null);
    }
    for (let i = 1; i <= ultimoDia.getDate(); i++) {
      dias.push(new Date(this.fechaActual.getFullYear(), this.fechaActual.getMonth(), i));
    }
    return dias;
  }

  getMesesAnio(): { nombre: string; numero: number }[] {
    return this.meses.map((nombre, numero) => ({ nombre, numero }));
  }

  esHoy(fecha: Date): boolean {
    return fecha.toDateString() === this.hoy.toDateString();
  }

  getCitasDia(fecha: Date): Cita[] {
    return this.citas.filter(c => c.fecha.toDateString() === fecha.toDateString());
  }

  getCitasHora(fecha: Date, hora: string): Cita[] {
    return this.getCitasDia(fecha).filter(c => c.horaInicio === hora);
  }

  tieneCitasMes(fecha: Date): boolean {
    return this.citas.some(c => c.fecha.toDateString() === fecha.toDateString());
  }

  getTopCita(hora: string): number {
    const [h] = hora.split(':').map(Number);
    return (h - 7) * 60;
  }

  getAlturaCita(cita: Cita): number {
    const [hI, mI] = cita.horaInicio.split(':').map(Number);
    const [hF, mF] = cita.horaFin.split(':').map(Number);
    return (hF * 60 + mF) - (hI * 60 + mI);
  }

  getTopCitaAbsoluto(cita: Cita): number {
    const [h, m] = cita.horaInicio.split(':').map(Number);
    return (h - 7) * 60 + m;
  }

  irAMesDesdeAnio(mes: number): void {
    this.fechaActual = new Date(this.fechaActual.getFullYear(), mes, 1);
    this.vistaActual = 'mes';
  }

  irASemanaDesdemes(fecha: Date): void {
    this.fechaActual = fecha;
    this.vistaActual = 'semana';
  }
  getDiasMiniMes(mes: number): (number | null)[] {
  const dias: (number | null)[] = [];
  const primerDia = new Date(this.fechaActual.getFullYear(), mes, 1);
  const ultimoDia = new Date(this.fechaActual.getFullYear(), mes + 1, 0);
  for (let i = 0; i < primerDia.getDay(); i++) {
    dias.push(null);
  }
  for (let i = 1; i <= ultimoDia.getDate(); i++) {
    dias.push(i);
  }
  return dias;
}

esHoyMes(dia: number, mes: number): boolean {
  return this.hoy.getDate() === dia &&
         this.hoy.getMonth() === mes &&
         this.hoy.getFullYear() === this.fechaActual.getFullYear();
}

tieneCitaMes(dia: number, mes: number): boolean {
  return this.citas.some(c =>
    c.fecha.getDate() === dia &&
    c.fecha.getMonth() === mes &&
    c.fecha.getFullYear() === this.fechaActual.getFullYear()
  );
}
}
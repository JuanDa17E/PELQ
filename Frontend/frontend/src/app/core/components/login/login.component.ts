import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { txt } from '../../constantes/txt.constants';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  t = txt;
  email: string = '';
  password: string = '';
  hayError: boolean = false;
  cargando: boolean = false;
  mostrarPassword: boolean = false;

  constructor(private router: Router) {}

  togglePassword(): void {
    this.mostrarPassword = !this.mostrarPassword;
  }

  iniciarSesion(): void {
    this.hayError = false;
    this.cargando = true;

    setTimeout(() => {
      this.cargando = false;
      if (this.email && this.password) {
        this.router.navigate(['/dashboard']);
      } else {
        this.hayError = true;
      }
    }, 1000);
  }
}
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginService } from '../services/tools/login.service';
import { AuthService } from '../services/tools/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMsg: string

  constructor(private loginService: LoginService, private authService: AuthService, private router: Router) {}

  login(f: NgForm) {
    let email = f.value.email
    let password = f.value.password

    const login = {
      email,
      password
    }

    console.log("Datos recibidos desde Login");
    console.log("Correo: "+email);
    console.log("Password: "+password);

    console.log("Conversión a JSON: "+JSON.stringify(login));

    this.loginService.login(login).subscribe(
      res => {
        console.log("Respuesta del servidor: \n"+JSON.stringify(res));

        this.authService.login(res);

        console.log("Objeto obtenido desde el storage: "+this.authService.isLoggedIn());
        
        // this.router.navigate(['inicio'])

        f.reset();
      },
      err => {
        // Personaliza los mensajes de error aquí
        if (err.status === 0) {
          this.errorMsg =
            'No se pudo conectar con el servidor. Verifica tu conexión.';
        } else if (err.status === 400) {
          this.errorMsg = err.error.message;
        } else if (err.status === 500) {
          this.errorMsg = 'Datos incorrectos enviados al servidor.';
        } else {
          this.errorMsg = 'Ocurrió un error desconocido.';
        }
        console.log(err.error.message);
        console.error('Error capturado:', this.errorMsg);
        alert(this.errorMsg)
        f.reset();
      }
    )
  }
}

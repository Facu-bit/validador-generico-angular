import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { CustomValidators } from '../validators/custom-validators';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  form: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService) {

    this.form = this.fb.group({

      email: [
        '',
        [Validators.required, Validators.email],
        [CustomValidators.uniqueValue(this.userService, 'email')]
      ],

      dni: [
        '',
        [Validators.required],
        [CustomValidators.uniqueValue(this.userService, 'dni')]
      ]
    });
  }

  // Getters para acceder fácil desde el HTML
  get email() { return this.form.get('email')!; }
  get dni()   { return this.form.get('dni')!; }

  onSubmit() {
    if (this.form.valid) {
      console.log('Formulario enviado:', this.form.value);
      alert('✅ Formulario válido!');
    }
  }
}

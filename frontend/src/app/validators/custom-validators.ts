import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { Observable, of, timer } from 'rxjs';
import { catchError, first, map, switchMap } from 'rxjs/operators';
import { UserService } from '../services/user.service';

export class CustomValidators {

  static uniqueValue(service: UserService, field: string): AsyncValidatorFn {

    return (control: AbstractControl): Observable<ValidationErrors | null> => {

      // Si el campo está vacío no validamos (deja que Validators.required lo maneje)
      if (!control.value) return of(null);

      return timer(500).pipe(

        // switchMap cancela la petición anterior si el usuario sigue escribiendo
        switchMap(() => service.checkValue(field, control.value)),

        // Si existe → error dinámico ej: { emailTaken: true }
        map(exists => exists ? { [`${field}Taken`]: true } : null),

        // Si el backend cae → no bloquea el formulario
        catchError(() => of(null)),

        first()
      );
    };
  }
}

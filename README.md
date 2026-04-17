# Validador Asíncrono Genérico - Angular + Spring Boot

## Descripción
Implementación de un validador asíncrono genérico (`uniqueValue`) que verifica la unicidad de distintos campos (email, DNI, etc.) contra una base de datos, usando Angular Reactive Forms y un backend en Spring Boot.

---

## Tecnologías
- **Frontend:** Angular 17+ (Reactive Forms + RxJS)
- **Backend:** Spring Boot 3.2 (API REST)
- **Base de datos:** H2 (en memoria)
- **Persistencia:** Spring Data JPA (`existsBy...`)

---

## Características
- ✅ Validador genérico `uniqueValue` — un solo validador para cualquier campo
- ✅ Debounce de 500ms con `timer()` para no saturar el servidor
- ✅ Cancelación automática de requests con `switchMap`
- ✅ Manejo de errores sin bloquear el formulario con `catchError`
- ✅ Feedback visual con estado PENDING ("Verificando...")
- ✅ Mensajes de error dinámicos por campo (`emailTaken`, `dniTaken`)

---

## Estructura del proyecto
```
validador-generico-angular/
├── backend/        → Spring Boot (API REST)
└── frontend/       → Angular (Reactive Forms)
```

---

## Instrucciones de ejecución

### 1. Backend (Spring Boot)
1. Abrí la carpeta `backend` en IntelliJ IDEA
2. IntelliJ detecta el `pom.xml` → aceptá abrirlo como proyecto Maven
3. Esperá que descargue las dependencias
4. Ejecutá la clase `ValidatorBackendApplication`
5. El servidor corre en `http://localhost:8080`

**Datos de prueba cargados automáticamente:**
- Email existente: `juan@gmail.com`
- DNI existente: `12345678`

**Probar endpoint:**
```
GET http://localhost:8080/api/validators/check-unique?field=email&value=juan@gmail.com
→ true

GET http://localhost:8080/api/validators/check-unique?field=email&value=nuevo@mail.com
→ false
```

---

### 2. Frontend (Angular)
1. Abrí PowerShell en la carpeta `frontend`
2. Instalá dependencias:
```powershell
npm install
```
3. Levantá el servidor:
```powershell
ng serve
```
4. Abrí el navegador en `http://localhost:4200`

> ⚠️ El backend tiene que estar corriendo antes de usar el formulario.

---

## Funcionamiento del validador genérico

```typescript
static uniqueValue(service: UserService, field: string): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    if (!control.value) return of(null);

    return timer(500).pipe(           // debounce 500ms
      switchMap(() => service.checkValue(field, control.value)), // cancela requests viejos
      map(exists => exists ? { [`${field}Taken`]: true } : null),
      catchError(() => of(null)),     // no bloquea si cae el backend
      first()
    );
  };
}
```

**Uso en el formulario:**
```typescript
email: ['', [Validators.required, Validators.email],
            [CustomValidators.uniqueValue(this.userService, 'email')]],

dni:   ['', [Validators.required],
            [CustomValidators.uniqueValue(this.userService, 'dni')]]
```

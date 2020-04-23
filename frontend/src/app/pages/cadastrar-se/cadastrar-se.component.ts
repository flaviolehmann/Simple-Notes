import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SegurancaService } from 'src/app/modules/seguranca/seguranca.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-se',
  templateUrl: './cadastrar-se.component.html',
  styleUrls: ['./cadastrar-se.component.scss']
})
export class CadastrarSeComponent implements OnInit {

  signUpForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private snackbar: MatSnackBar,
    private segurancaService: SegurancaService,
    private router: Router
  ) { }

  ngOnInit() {
    this.signUpForm = this.fb.group({
      username: ['', Validators.required],
      password0: ['', Validators.required],
      password1: ['', Validators.required]
    });
  }

  onSubmit(): void {
    const formValue = this.signUpForm.value;
    if (formValue.password0 !== formValue.password1) {
      this.snackbar.open('Your passwords doesn\'t match', 'Ok', { duration: 7000 });
      return;
    }

    this.loading = true;
    this.segurancaService.signup(formValue.username, formValue.password0)
      .subscribe({
        next: () => {
          this.snackbar.open('You\'re now signed up! Proceed to login.', 'Ok', { duration: 3500 });
          this.signUpForm.reset();
          this.router.navigateByUrl('login');
        },
        error: err => {
          this.snackbar.open('Error! Please, check your data and try again.', 'Ok', { duration: 7000 });
          console.error(err);
        }
      }).add(() => this.loading = false);
  }

}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SegurancaService } from '../../modules/seguranca/seguranca.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private segurancaService: SegurancaService,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      user: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(form: FormGroup) {
    this.loading = true;
    const credentials = form.value;
    this.segurancaService.login(credentials.user, credentials.password).subscribe(
    (res: HttpResponse<any>) => {
        console.log(res)
        this.segurancaService.token = res.headers.get('Authorization').substring(7);
        this.router.navigateByUrl('main');
        form.reset();
    },
    err => {
        console.log(err);
        this.snackbar.open('Sorry, check your password and username.', 'Ok', { duration: 4500 });
      }
    ).add(() => this.loading = false);
  }

}

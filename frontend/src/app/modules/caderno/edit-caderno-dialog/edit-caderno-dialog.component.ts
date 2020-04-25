import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CadernoService } from '../caderno.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Caderno } from '../caderno.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-caderno-dialog',
  templateUrl: './edit-caderno-dialog.component.html'
})
export class EditCadernoDialogComponent implements OnInit {

  loading = false;
  caderno: Caderno;
  cadernoForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EditCadernoDialogComponent>,
    private cadernoService: CadernoService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    if (data && data.caderno) {
      this.caderno = data.caderno;
    }
  }

  ngOnInit(): void {
    this.cadernoForm = this.fb.group({
      nome: ['', Validators.required],
      descricao: ['', Validators.required]
    });

    if (this.caderno) {
      this.cadernoForm.get('nome').setValue(this.caderno.nome);
      this.cadernoForm.get('descricao').setValue(this.caderno.descricao);
    }
  }

  onSubmit(): void {
    this.loading = true;

    if (this.caderno) {
      this.cadernoService.update({
        ...this.cadernoForm.value,
        id: this.caderno.id
      }).subscribe({
        next: novoCaderno => {
          this.snackBar.open('Successfuly updated the notebook!');
          this.dialogRef.close(novoCaderno);
        },
        error: () => this.snackBar.open('Something went wrong, check your data and try again.', 'Ok')
      }).add(() => this.loading = false);
    }
    else {
      this.cadernoService.create(this.cadernoForm.value).subscribe({
        next: novoCaderno => {
          this.snackBar.open('Successfuly created new notebook!');
          this.dialogRef.close(novoCaderno);
        },
        error: () => this.snackBar.open('Error, please check your data.', 'Ok')
      }).add(() => this.loading = false);
    }

  }

  onCancel(): void {
    this.dialogRef.close();
  }

}

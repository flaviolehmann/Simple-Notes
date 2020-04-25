import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Nota } from '../nota.model';
import { Caderno } from '../../caderno/caderno.model';
import { NotaService } from '../nota.service';

@Component({
  selector: 'app-edit-nota-dialog',
  templateUrl: './edit-nota-dialog.component.html'
})
export class EditNotaDialogComponent implements OnInit {

  loading = false;
  caderno: Caderno;
  nota: Nota;
  notaForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EditNotaDialogComponent>,
    private notaService: NotaService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    if (data && data.caderno) {
      this.caderno = data.caderno;
      this.nota = data.nota;
    }
  }

  ngOnInit(): void {
    this.notaForm = this.fb.group({
      titulo: ['', Validators.required],
      descricao: ['', Validators.required]
    });

    if (this.nota) {
      this.notaForm.get('titulo').setValue(this.nota.titulo);
      this.notaForm.get('descricao').setValue(this.nota.descricao);
    }
  }

  onSubmit(): void {
    this.loading = true;
    this.nota ? this.updateNote() : this.createNewNote();
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  private createNewNote(): void {
    this.notaService.create({
      ...this.notaForm.value,
      caderno: this.caderno
    }).subscribe({
      next: novoCaderno => {
        this.snackBar.open('Successfuly created new note!');
        this.dialogRef.close(novoCaderno);
      },
      error: () => this.snackBar.open('Error, please check your data.', 'Ok')
    }).add(() => this.loading = false);
  }

  private updateNote(): void {
    this.notaService.update({
      ...this.notaForm.value,
      id: this.nota.id,
      caderno: this.caderno
    }).subscribe({
      next: novoCaderno => {
        this.snackBar.open('Successfuly updated the note!');
        this.dialogRef.close(novoCaderno);
      },
      error: () => this.snackBar.open('Something went wrong, check your data and try again.', 'Ok')
    }).add(() => this.loading = false);
  }
}

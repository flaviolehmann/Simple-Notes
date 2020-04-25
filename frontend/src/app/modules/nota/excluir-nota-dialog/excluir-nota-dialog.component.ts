import { Component, OnInit, Inject } from '@angular/core';
import { Nota } from '../nota.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotaService } from '../nota.service';

@Component({
  selector: 'app-excluir-nota-dialog',
  templateUrl: './excluir-nota-dialog.component.html'
})
export class ExcluirNotaDialogComponent  {

  nota: Nota;

  constructor(
    public dialogRef: MatDialogRef<ExcluirNotaDialogComponent>,
    private notaService: NotaService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.nota = data.nota;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    this.notaService.destroy(this.nota.id).subscribe(() => {
      this.snackBar.open('Successfuly deleted note!', 'Ok', { duration: 3500 });
      this.dialogRef.close(true);
    });
  }

}

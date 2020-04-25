import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CadernoService } from '../caderno.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Caderno } from '../caderno.model';

@Component({
  selector: 'app-excluir-caderno-dialog',
  templateUrl: './excluir-caderno-dialog.component.html'
})
export class ExcluirCadernoDialogComponent {

  caderno: Caderno;

  constructor(
    public dialogRef: MatDialogRef<ExcluirCadernoDialogComponent>,
    private cadernoService: CadernoService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    this.caderno = data.caderno;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    this.cadernoService.destroy(this.caderno.id).subscribe(() => {
      this.snackBar.open('Successfuly deleted notebook!', 'Ok', { duration: 3500 });
      this.dialogRef.close(true);
    });
  }
}

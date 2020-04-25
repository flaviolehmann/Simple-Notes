import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Nota } from '../nota.model';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Caderno } from '../../caderno/caderno.model';
import { EditNotaDialogComponent } from '../edit-nota-dialog/edit-nota-dialog.component';
import { ExcluirNotaDialogComponent } from '../excluir-nota-dialog/excluir-nota-dialog.component';


@Component({
  selector: 'app-nota-card',
  templateUrl: './nota-card.component.html',
  styleUrls: ['./nota-card.component.scss']
})
export class NotaCardComponent {

  @Input() caderno: Caderno;
  @Input() nota: Nota;
  @Output() updatedNota = new EventEmitter();

  constructor(
    private dialog: MatDialog
  ) { }

  onEdit(): void {
    this.dialog.open(EditNotaDialogComponent, {
      ...new MatDialogConfig(),
      width: '400px',
      autoFocus: false,
      data: {
        caderno: this.caderno,
        nota: this.nota
      }
    }).afterClosed().subscribe(() => this.updatedNota.emit());
  }

  onDelete(): void {
    this.dialog.open(ExcluirNotaDialogComponent, {
      ...new MatDialogConfig(),
      autoFocus: false,
      width: '400px',
      data: { nota: this.nota }
    }).afterClosed().subscribe(res => res && this.updatedNota.emit());
  }
}

import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Caderno } from 'src/app/modules/caderno/caderno.model';
import { NavBarService } from 'src/app/main-nav/nav-bar.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { EditCadernoDialogComponent } from 'src/app/modules/caderno/edit-caderno-dialog/edit-caderno-dialog.component';
import { CadernoService } from 'src/app/modules/caderno/caderno.service';
import { ExcluirCadernoDialogComponent } from 'src/app/modules/caderno/excluir-caderno-dialog/excluir-caderno-dialog.component';
import { EditNotaDialogComponent } from 'src/app/modules/nota/edit-nota-dialog/edit-nota-dialog.component';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  caderno: Caderno;
  @Output() updateCadernos = new EventEmitter();

  constructor(
    private navBarService: NavBarService,
    private cadernoService: CadernoService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.navBarService.cadernoSelecionado$.subscribe(caderno => this.caderno = caderno);
  }

  onEdit(caderno: Caderno): void {
    this.dialog.open(EditCadernoDialogComponent, {
      ...new MatDialogConfig(),
      autoFocus: false,
      width: '400px',
      data: { caderno }
    }).afterClosed().subscribe(novoCaderno => {
      if (novoCaderno) {
        this.navBarService.cadernoSelecionado = novoCaderno;
        window.location.reload();
      }
    });
  }

  onDelete(): void {
    this.dialog.open(ExcluirCadernoDialogComponent, {
      ...new MatDialogConfig(),
      autoFocus: false,
      width: '400px',
      data: { caderno: this.caderno }
    }).afterClosed().subscribe(res => res && window.location.reload());
  }

  onUpdateSomeNota(): void {
    this.cadernoService.show(this.caderno.id)
      .subscribe(caderno => this.caderno = caderno);
  }

  onAddNota(): void {
    console.log({caderno: this.caderno})
    this.dialog.open(EditNotaDialogComponent, {
      ...new MatDialogConfig(),
      width: '400px',
      autoFocus: false,
      data: { caderno: this.caderno }
    }).afterClosed().subscribe(() => this.onUpdateSomeNota());
  }
}

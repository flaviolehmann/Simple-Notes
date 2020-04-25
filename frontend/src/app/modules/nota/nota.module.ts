import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditNotaDialogComponent } from './edit-nota-dialog/edit-nota-dialog.component';
import { NotaCardComponent } from './nota-card/nota-card.component';
import { ExcluirNotaDialogComponent } from './excluir-nota-dialog/excluir-nota-dialog.component';



@NgModule({
  declarations: [EditNotaDialogComponent, NotaCardComponent, ExcluirNotaDialogComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    NotaCardComponent
  ]
})
export class NotaModule { }

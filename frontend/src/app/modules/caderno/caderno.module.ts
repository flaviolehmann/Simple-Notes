import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditCadernoDialogComponent } from './edit-caderno-dialog/edit-caderno-dialog.component';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExcluirCadernoDialogComponent } from './excluir-caderno-dialog/excluir-caderno-dialog.component';


@NgModule({
  declarations: [EditCadernoDialogComponent, ExcluirCadernoDialogComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class CadernoModule { }

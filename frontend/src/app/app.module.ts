import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './pages/main/main.component';
import { LoginComponent } from './pages/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainNavComponent } from './main-nav/main-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MaterialModule } from './material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CadastrarSeComponent } from './pages/cadastrar-se/cadastrar-se.component';
import { SegurancaModule } from './modules/seguranca/seguranca.module';
import { NotaModule } from './modules/nota/nota.module';
import { CadernoModule } from './modules/caderno/caderno.module';
import { EditCadernoDialogComponent } from './modules/caderno/edit-caderno-dialog/edit-caderno-dialog.component';
import { EditNotaDialogComponent } from './modules/nota/edit-nota-dialog/edit-nota-dialog.component';
import { ExcluirCadernoDialogComponent } from './modules/caderno/excluir-caderno-dialog/excluir-caderno-dialog.component';
import { ExcluirNotaDialogComponent } from './modules/nota/excluir-nota-dialog/excluir-nota-dialog.component';

const getApiUrl = (currentUrl: string) => {
  switch (currentUrl) {
    // case 'https://seguidor-tcc.herokuapp.com':
    //   return 'https://backend-seguidor1.herokuapp.com';

    default:
      return 'http://127.0.0.1:8080';
  }
}

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    CadastrarSeComponent,
    MainNavComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    LayoutModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    SegurancaModule,
    CadernoModule,
    NotaModule
  ],
  providers: [
    { provide: 'defaultURL', useValue: getApiUrl(window.location.origin) }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    EditCadernoDialogComponent,
    EditNotaDialogComponent,
    ExcluirCadernoDialogComponent,
    ExcluirNotaDialogComponent
  ]
})
export class AppModule { }

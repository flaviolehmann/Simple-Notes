import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SegurancaService } from '../modules/seguranca/seguranca.service';
import { Caderno } from '../modules/caderno/caderno.model';
import { CadernoService } from '../modules/caderno/caderno.service';
import { NavBarService } from './nav-bar.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { EditCadernoDialogComponent } from '../modules/caderno/edit-caderno-dialog/edit-caderno-dialog.component';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss']
})
export class MainNavComponent implements OnInit {

  cadernos: Caderno[];

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private segurancaService: SegurancaService,
    private cadernoService: CadernoService,
    private navBarService: NavBarService,
    private dialog: MatDialog
    ) {}

  onLogout(): void {
    this.router.navigateByUrl('/login');
    this.segurancaService.logout();
  }

  ngOnInit(): void {
    this.cadernoService.index().subscribe(cadernos => this.cadernos = cadernos);
  }

  onSelectCaderno(caderno: Caderno): void {
    this.cadernoService.show(caderno.id).subscribe(
      caderno => this.navBarService.cadernoSelecionado = caderno
    );
  }

  onAddCaderno(): void {
    this.dialog.open(EditCadernoDialogComponent, {
      ...new MatDialogConfig(),
      autoFocus: false,
      width: '400px'
    }).afterClosed().subscribe(() => this.ngOnInit());
  }
}

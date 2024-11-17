import { Component } from '@angular/core';
import { NavIconComponent } from '../nav-icon/nav-icon.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [NavIconComponent, RouterOutlet],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {}

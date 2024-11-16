import { Component, OnInit } from '@angular/core';
import { AboutService } from '../../services/about.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-about',
  standalone: true,
  imports: [NgIf],
  templateUrl: './about.component.html',
  styleUrl: './about.component.scss',
})
export class AboutComponent implements OnInit {
  aboutContent: { title: string; content: string } | undefined;

  constructor(private aboutService: AboutService) {}

  ngOnInit(): void {
    this.aboutContent = this.aboutService.getAboutContent();
  }
}

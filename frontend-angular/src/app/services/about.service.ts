import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AboutService {
  constructor() {}

  // Fetch dynamic content for the about page
  getAboutContent() {
    return {
      title: 'About Us',
      content: 'We are passionate developers creating amazing applicaitons',
    };
  }
}

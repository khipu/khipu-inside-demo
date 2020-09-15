import { Component } from '@angular/core';
import khenshin from 'cordova-khenshin/www/khenshin';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  constructor() {}
  doPay() {
    khenshin.startByPaymentId('bfwdma6lq7vk', success => console.log(success), err => console.log(err));
  }
}

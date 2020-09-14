import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CommentEngineSharedModule } from 'app/shared/shared.module';
import { CommentEngineCoreModule } from 'app/core/core.module';
import { CommentEngineAppRoutingModule } from './app-routing.module';
import { CommentEngineHomeModule } from './home/home.module';
import { CommentEngineEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CommentEngineSharedModule,
    CommentEngineCoreModule,
    CommentEngineHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CommentEngineEntityModule,
    CommentEngineAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class CommentEngineAppModule {}

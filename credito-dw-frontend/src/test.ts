import 'zone.js/testing';
import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting,
} from '@angular/platform-browser-dynamic/testing';

declare const require: {
  context(
    path: string,
    deep?: boolean,
    filter?: RegExp
  ): {
    keys: () => string[];
    <T>(id: string): T;
  };
};

// Configura o ambiente de testes Angular
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);

// Carrega todos os arquivos .spec.ts
require.context('./', true, /\.spec\.ts$/);

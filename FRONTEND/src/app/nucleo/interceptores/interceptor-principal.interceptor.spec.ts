import { TestBed } from '@angular/core/testing';

import { InterceptorPrincipalInterceptor } from './interceptor-principal.interceptor';

describe('InterceptorPrincipalInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      InterceptorPrincipalInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: InterceptorPrincipalInterceptor = TestBed.inject(InterceptorPrincipalInterceptor);
    expect(interceptor).toBeTruthy();
  });
});

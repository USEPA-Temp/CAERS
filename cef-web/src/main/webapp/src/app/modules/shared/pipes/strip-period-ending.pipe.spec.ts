import { StripPeriodEndingPipe } from './strip-period-ending.pipe';

describe('StripPeriodEndingPipe', () => {
  it('create an instance', () => {
    const pipe = new StripPeriodEndingPipe();
    expect(pipe).toBeTruthy();
  });
});

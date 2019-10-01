export class SccCode {
  uid: string;
  code: number;
  attributes: {[key: string]: any};

  public constructor(init?: Partial<SccCode>) {
    Object.assign(this, init);
  }

  get sector(): string {
    return this.get('sector');
  }

  get description(): string {
    return `${this.get('scc level one')} > ${this.get('scc level two')} > ${this.get('scc level three')} > ${this.get('scc level four')}`;
  }

  get(path: string) {
    if (this.attributes && this.attributes[path]) {
      return this.attributes[path].text;
    } else {
      return null;
    }
  }
}

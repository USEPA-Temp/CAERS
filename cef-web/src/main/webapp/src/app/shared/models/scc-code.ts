/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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

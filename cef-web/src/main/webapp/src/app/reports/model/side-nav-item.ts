import { EmissionUnit } from './emission-unit';
import { Process } from './process';
import { ReleasePointApportionment } from './release-point-apportionment';

export class SideNavItem {
  baseUrl: string;
  id: number;
  description: string;
  children: SideNavItem[];

  constructor(id: number, description: string, baseUrl: string, children: SideNavItem[]) {
    this.id = id;
    this.description = description;
    this.baseUrl = baseUrl;
    this.children = children;
  }

  get url(): string {
    return `${this.baseUrl}/${this.id}`;
  }

  static fromEmissionUnit(unit: EmissionUnit): SideNavItem {
    return new SideNavItem(unit.id, unit.description, 'emissionUnit', 
      unit.processes.map(process => this.fromProcess(process)));
  }

  static fromProcess(process: Process): SideNavItem {
    return new SideNavItem(process.id, process.description, 'process', 
      process.releasePoints.map(release => this.fromReleasePoint(release)));
  }

  static fromReleasePoint(release: ReleasePointApportionment): SideNavItem {
    return new SideNavItem(release.id, release.releasePoint.description, 'release', null);
  }
}
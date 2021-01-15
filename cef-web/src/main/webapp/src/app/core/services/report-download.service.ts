import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ReportDownloadService {

    constructor() {}

    downloadFile(data?: any, filename = 'data', dataCsv?: string) {

        const csvData = dataCsv ? dataCsv : this.ConvertToCSV(data, ['facilitySiteId', 'reportYear', 'emissionsUnitId', 'emissionUnitDescription',
            'processId', 'processDescription', 'reportingPeriodType', 'throughputMaterial', 'throughputValue', 'throughputUom','fuelMaterial', 
            'fuelValue', 'fuelUom', 'heatContentValue', 'heatContentUom', 'pollutantName',
            'totalEmissions', 'emissionsUomCode', 'overallControlPercent', 'emissionsCalcMethod', 'emissionsFactor',
            'emissionsNumeratorUom', 'emissionsDenominatorUom', 'emissionsFactorText', 'emissionsComment', 'calculationComment',
            'lastModifiedBy', 'lastModifiedDate']);

        const blob = new Blob(['\ufeff' + csvData], {type: 'text/csv;charset=utf-8;'});
        const dwldLink = document.createElement('a');
        let url;
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(blob, filename + '.csv');
            return;
        } else {
            url = URL.createObjectURL(blob);
        }

        const isSafariBrowser = navigator.userAgent.indexOf('Safari') !== -1 && navigator.userAgent.indexOf('Chrome') === -1;
        if (isSafariBrowser) {  // if Safari open in new window to save file with random filename.
            dwldLink.setAttribute('target', '_blank');
        }
        dwldLink.setAttribute('href', url);
        dwldLink.setAttribute('download', filename + '.csv');
        dwldLink.style.visibility = 'hidden';
        document.body.appendChild(dwldLink);
        dwldLink.click();
        document.body.removeChild(dwldLink);
    }

    ConvertToCSV(objArray: string, headerList: string[]) {
        const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
        let str = '';
        let row = 'S.No,';

        for (const index in headerList) {
            row += headerList[index] + ',';
        }
        row = row.slice(0, -1);
        str += row + '\r\n';
        for (let i = 0; i < array.length; i++) {
            let line = (i + 1) + '';
            for (const index in headerList) {
                const head = headerList[index];
                line += ',' + array[i][head];
            }
            str += line + '\r\n';
        }
        return str;
    }
}


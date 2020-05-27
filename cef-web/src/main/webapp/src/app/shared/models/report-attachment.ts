export class ReportAttachment {
  id: number;
  reportId: number;
  attachment: Blob;
  fileName: string;
  comments: string;
  fileType: string;
}

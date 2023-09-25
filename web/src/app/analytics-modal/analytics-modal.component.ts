import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';
import { Chart, TimeUnit } from 'chart.js/dist';
import { AnalyticsService } from '../_services/analytics.service';
import { BaseChartDirective } from 'ng2-charts';
import { FormControl, FormGroup } from '@angular/forms';
import * as dayjs from 'dayjs';
declare var UIkit: any;
@Component({
  selector: 'app-analytics-modal',
  templateUrl: './analytics-modal.component.html',
  styleUrls: ['./analytics-modal.component.css'],
})
export class AnalyticsModalComponent implements AfterViewInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  constructor(private analyticsService: AnalyticsService) {}

  dateOptions: { name: string; value: 'hour' | 'day' | 'week' }[] = [
    { name: 'Last Hour', value: 'hour' },
    { name: 'Today', value: 'day' },
    { name: 'Last week', value: 'week' },
  ];

  defaultOption = this.dateOptions[0];

  form = new FormGroup({
    dateOption: new FormControl(this.defaultOption),
  });

  ngAfterViewInit(): void {
    this.form.controls.dateOption.valueChanges.subscribe({
      next: (newValue) => {
        console.log('New value', newValue);
        this.loadData(this.shortUrlPath, newValue.value);
      },
    });
  }

  loading = false;

  shortUrlPath = '';

  public lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [],
  };
  public lineChartOptions: ChartOptions<'line'> = {};
  public lineChartLegend = true;

  show(shortUrlPath: string) {
    UIkit.modal('#analytics-modal').show();
    this.loadData(shortUrlPath);
  }

  loadData(
    shortUrlPath: string,
    option: 'hour' | 'day' | 'week' = this.defaultOption.value
  ) {
    this.shortUrlPath = shortUrlPath;
    let startDate = dayjs.utc().startOf(option);
    let endDate = dayjs.utc().endOf(option);
    this.loading = true;

    this.analyticsService
      .getAnalytics(shortUrlPath, startDate, endDate, this.getUnit(option))
      .subscribe({
        next: (res) => {
          // console.log(res.map((d) => dayjs.utc(d.dateTime).local().format()));
          setTimeout(() => {
            this.lineChartData = {
              labels: res.map((d) => dayjs.utc(d.dateTime).local().format()),
              datasets: [
                {
                  data: res.map((d) => d.count),
                  label: 'Audience',
                  borderColor: '#1e87f0',
                  tension: 0.1,
                  fill: false,
                  backgroundColor: '#fff',
                  pointBackgroundColor: '#222',
                },
              ],
            };
            this.lineChartOptions = {
              responsive: false,
              scales: {
                y: {
                  type: 'linear'
                },
                x: {
                  type: 'time',
                  time: {
                    unit: this.getUnit(
                      this.form.controls.dateOption.value.value
                    ),
                    displayFormats: {
                      minute: 'mm',
                      hour: 'LT',
                      day: 'dddd DD',
                    },
                  },
                },
              },
            };
            this.setLoading(false);
          }, 100);
          console.log(res);
          // console.log(this.data);
          // console.log(this.labels);
          this.shortUrlPath = shortUrlPath;
        },
        error: (err) => {
          console.log(err);
          this.loading = false;
        },
      });
  }

  setLoading(v: boolean) {
    this.loading = v;
  }

  getUnit(
    option: 'hour' | 'day' | 'week' = this.defaultOption.value
  ): 'minute' | 'hour' | 'day' {
    if (option == 'hour') return 'minute';
    if (option == 'day') return 'hour';
    return 'day';
  }
}

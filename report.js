/*
 * Copyright (c) 2023. caoccao.com Sam Cao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This application is a report collector and document generator.
 * It scans the test reports, extracts the test results and generates the document.
 */

const fs = require('fs');
const path = require('path');

const htmlReportPath = 'docs/index.html';
const reportMap = {};
const testReportPath = 'build/reports/tests/test/classes';

function collectTestReports(rootPath, reportMap) {
  console.info(`Scanning ${rootPath}.`);
  fs.readdirSync(rootPath, { withFileTypes: true }).forEach(file => {
    if (file.isDirectory()) {
      collectTestReports(file, reportMap);
    } else if (file.isFile() && file.name.endsWith('.html')) {
      console.info(`  Processing ${file.name}.`);
      const content = fs.readFileSync(path.join(rootPath, file.name), { encoding: 'utf8' });
      for (const versionMatch of content.matchAll(/Javet version is ([\d\.]+)/g)) {
        const version = versionMatch[1].replaceAll(/\.+$/g, '');
        for (const match of content.matchAll(/\[\s*(v8|node)\]\s*(\w+): [\w\s\.]* (\d+)\./gm)) {
          const type = match[1];
          const name = match[2];
          const data = match[3];
          let versionedReportMap = reportMap[version];
          if (versionedReportMap === undefined) {
            versionedReportMap = {};
            reportMap[version] = versionedReportMap;
          }
          let namedReportMap = versionedReportMap[name];
          if (namedReportMap === undefined) {
            namedReportMap = {};
            versionedReportMap[name] = namedReportMap;
          }
          namedReportMap[type] = data;
        }
        break;
      }
    }
  });
}

function generateDocument(htmlReportPath, reportMap) {
  const originalContent = fs.readFileSync(htmlReportPath, { encoding: 'utf8' });
  const lines = [];
  originalContent.split('\n').forEach(line => {
    line = line.trimEnd();
    if (line.startsWith('        reportMap[')) {
      const startIndex = line.indexOf("'");
      const endIndex = line.indexOf("'", startIndex + 1);
      const version = line.substring(startIndex + 1, endIndex);
      if (version in reportMap) {
        const data = reportMap[version];
        line = `        reportMap['${version}'] = ${JSON.stringify(data)};`;
      }
    }
    lines.push(line);
  });
  const newContent = lines.join('\n');
  console.info();
  if (originalContent != newContent) {
    fs.writeFileSync(htmlReportPath, newContent);
    console.info(`Generated ${htmlReportPath}.`);
  } else {
    console.info(`Skipped ${htmlReportPath}.`);
  }
}

collectTestReports(testReportPath, reportMap);
generateDocument(htmlReportPath, reportMap);

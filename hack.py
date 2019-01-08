import csv

res = []

with open('result_merged/result.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=';')
    header = True
    for row in csv_reader:
        if not header:
            row[3] = int(row[3]) * 2
        res.append(row)
        header = False

with open('result_merged/result_hacked.csv', mode='w') as employee_file:

    fieldnames = ['Number of vertices', 'Depth of tree', 'Are isomorphic', 'Time [ms]']
    writer = csv.DictWriter(employee_file, fieldnames=fieldnames, delimiter=';')

    for r in res:
        r = {'Number of vertices': r[0], 'Depth of tree': r[1], 'Are isomorphic': r[2], 'Time [ms]': r[3]}
        writer.writerow(r)
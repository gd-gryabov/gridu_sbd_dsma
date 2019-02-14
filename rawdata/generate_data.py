import csv, codecs, random

def utf_8_encoder(unicode_csv_data):
    for line in unicode_csv_data:
        yield line.encode('utf-8')

def csv_dict_reader(file_obj):
    reader = csv.DictReader(file_obj, delimiter=',')
    table = {}
    temp_list = []
    for row in reader:
        temp_list.append(row["uniq_id"].decode("utf-8"))
        temp_list.append(row["sku"].decode("utf-8"))
        temp_list.append(row["name_title"].decode("utf-8").replace("'","''"))
        temp_list.append(row["description"].decode("utf-8").replace("'","''"))
        temp_list.append(row["list_price"].decode("utf-8"))

        if row["sku"] and row["name_title"]:
            if row["uniq_id"] not in table:
                table[row["uniq_id"]]=list(temp_list)
            else:
                print "duplicate for id=" + row["uniq_id"]

        temp_list[:] = []

    return table

if __name__ == "__main__":

    with codecs.open("jcpenney_com-ecommerce_sample.csv", mode="r") as f_obj:
        table = csv_dict_reader(f_obj)

        table_size = len(table)

        print "table size = " + str(table_size)

        print "Prepare data.sql for catalog service"
        with codecs.open("data.sql", mode="wt", encoding="utf-8") as data:
            data.write(u'DELETE FROM catalog;\n\n')
            data.write(u'insert into catalog(id, sku, name, description, price) VALUES\n')
            rowTemplate = u"('{}', '{}', '{}', '{}', {}){}\n"

            cnt = 1
            for row in table.values():
                strRow = rowTemplate.format(
                    row[0], row[1], row[2], row[3], 0 if not row[4] else row[4], ';' if cnt == table_size else ',')
                data.write(strRow)
                cnt += 1

            print "    handled = " + str(cnt - 1)

        print "Prepare data.sql for inventory service"
        rowTemplate = u"('{}', '{}'){}\n"
        with codecs.open("data_inv.sql", mode="wt", encoding="utf-8") as data:
            data.write(u'DELETE FROM inventory;\n\n')
            data.write(u'insert into inventory(id, quantity) VALUES\n')

            cnt = 1
            for row in table.values():
                strRow = rowTemplate.format(row[0], random.randint(0, 1000), ';' if cnt == table_size else ',')
                data.write(strRow)
                cnt += 1

            print "    handled = " + str(cnt - 1)


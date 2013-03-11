class TimesheetToHtml:
    """ Class that converts our timesheet to a HTML file."""
    def __init__(self, timesheet):
        """Initialize the timesheet by reading it and parsing it."""
        file = open(timesheet, "r")
        lines = file.readlines()
        lines = filter(lambda item: (item != "\n" and not
                                     item.startswith("#")), lines)
        self.fields = [x.strip() for x in lines[0].split("|")]
        self.lines = lines[2:]
        time_spent = []
        for line in self.lines:
            fields = [x.strip() for x in line.split("|")]
            time_spent.append(fields)
        self.time_spent = time_spent

    def get_tasks_table_html(self):
        """ Create and return the task HTML table. """
        table = '<table class="table table-bordered"> <tr>'
        for field in self.fields:
            table += "<th> %s </th>" % field.strip()
        table += "</tr>"

        for line in self.lines:
            table += "<tr>"
            i = 0
            for field in line.split("|"):
                if field.strip() == "7" and i == 2:
                    table += "<td> Reading week </td>"
                else:
                    table += "<td> %s </td>" % field.strip()
                i += 1
            table += "</tr>"

        return table

    def get_timesheet_table_html(self):
        """ Create and return the timesheet HTML table. """
        table = '<table class="table table-striped table-bordered"> <tr> '
        table += "<th> Member </th>"

        for i in range(6):
            table += "<th> Week %d </th>" % (i+1)

        table += "<th> Reading Week </th>"
        table += "<th> Total </th>"

        members = ["Alex", "Arthur", "Francois", "Hassan", "Hong Yi" "Kaichen"]

        for member in members:
            table += "<tr>"
            table += "<td> %s </td>" % member
            for i in range(7):
                table += "<td> %4.2f </td>" % (self.get_hours(member, i+1))
            table += "<td> %4.2f </td>" % (self.get_total_hours(member))
            table += "</tr>"

        table += "</table>"

        return table

    def get_hours(self, member, week):
        """ Get hours from a particular member for a given week. """
        lines = []
        for line in self.time_spent:
            if int(line[2]) == week and line[0] == member:
                lines.append(line)

        if len(lines) == 0:
            return 0
        else:
            sum = 0.0
            for line in lines:
                sum += float(line[3])
            return sum

    def get_total_hours(self, member):
        """ Get total hours from a particular member for a given week.
        In particular, we exclude the reading week hours from the total. """
        total = 0.0
        for i in range(6):
            total += self.get_hours(member, i+1)

        return total

    def get_html_header(self):
        """ Get the HTML header templates. """
        return "\n".join(["<!doctype html>",
                          "<head>",
                          "<title> DPM Group 6 Timesheet </title>",
                          self.get_meta_and_stylesheet(),
                          "</head>",
                          "<body>",
                          '<div class="container-fluid">',
                          '<div class="center hero-unit">',
                          '<h1>DPM Group 6</h1>',
                          '<h2>Weekly Budget Allocation</h2>',
                          '</div>'])

    def get_meta_and_stylesheet(self):
        """ Get the HTML meta and CSS templates for the page. """
        return "\n".join(
            ['<meta http-equiv="Content-Type" ' +
             'content="text/html; charset=UTF-8"/>',
             '<meta name="viewport" content="width=device-width, ' +
             'initial-scale=1.0"/>',
             '<link rel="stylesheet" href="bootstrap.css" ' +
             'type="text/css"/>'])

    def get_html_footer(self):
        """ Get the HTML footer template. """
        return "\n".join(["</div>",
                          "</div>",
                          "</body>",
                          "</html>"])

if __name__ == "__main__":
    timesheet_file = "timesheet.txt"
    output_file = "index.html"

    timesheetCreator = TimesheetToHtml(timesheet_file)
    timesheet_table = timesheetCreator.get_timesheet_table_html()
    task_table = timesheetCreator.get_tasks_table_html()

    with open(output_file, "w") as html:
        html.write(timesheetCreator.get_html_header())
        html.write('<h2 class="center">Weekly Hours Budget</h2>')
        html.write(timesheet_table)
        html.write('<h2 class="center">Tasks</h2>')
        html.write(task_table)
        html.write(timesheetCreator.get_html_footer())

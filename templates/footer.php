<?php
/*
 *  Free Room Finder Website
 *
 *  Copyright (C) 2013 Amit Jain, Anthony Jihn, Jonathan Gillett, and Joseph Heron
 *  All rights reserved.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * The default footer for the website, displays copyright, etc.
 */
?>
      <footer class="footer">
        <div class="pull-left">
          <p>Designed and built by <a target="_blank" href="#">Crow's Foot</a></p>
          <p>This website is Open Source, code licensed under the 
          	<a target="_blank" href="http://www.gnu.org/licenses/agpl-3.0.html">GNU Affero General Public License, Version 3</a>
          </p>
          <p><strong>&copy; Crow's Foot</strong></p>
        </div>
      </footer>
    </div>
    <!-- Javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.8.3.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/highcharts/highcharts.js"></script>
    <script>
      $(function(){
        $('#select_date').datepicker({
          format: 'yyyy-mm-dd'
        });
      });
    </script>
    <script src="js/statistics.js"></script>
  </body>
</html>
<?php
/*
 *  Free Room Finder Website
 *
 *
 *  Authors -- Crow's Foot Group
 *  -------------------------------------------------------
 *
 *  Jonathan Gillett
 *  Joseph Heron
 *  Amit Jain
 *  Wesley Unwin
 *  Anthony Jihn
 * 
 *
 *  License
 *  -------------------------------------------------------
 *
 *  Copyright (C) 2012 Crow's Foot Group
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
          <p>Designed and built by <a target="_blank" href="#">Crow'sFoot</a></p>
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
    <script>
      $(function(){
        $('#dp1').datepicker({
          format: 'mm-dd-yyyy'
        });
      });
    </script>
    <!--<script src="js/custom.js"></script>-->
  </body>
</html>